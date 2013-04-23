package demo.macro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ArticleDAO {
	private List<Article> articles = new ArrayList<Article>(100);
	static ArticleDAO instance;
	
	public static synchronized ArticleDAO getInstance(){
		if (instance == null) {
			instance = new ArticleDAO();
		}
		return instance;
	}
	
	private ArticleDAO() {
		for (int i = 0; i < 100; i++)
			articles.add(new Article(i + 1));
		Collections.sort(articles, new DateComparator());
	}
	
	public Map<Integer, List<Article>> countByYear() {
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(articles.get(0).getPostDate());
		int lastest = cal.get(Calendar.YEAR);
		
		cal.setTime(articles.get(articles.size()-1).getPostDate());
		int oldest = cal.get(Calendar.YEAR);
		
		Map<Integer, List<Article>> countEachYear = new TreeMap<Integer, List<Article>>(new IntegerComparator());
		List<Article> temp = new ArrayList<Article>(articles);
		for (int i = lastest; i >= oldest; i--) {
			List<Article> list = new ArrayList<Article>();
			for (Iterator<Article> it = temp.iterator(); it.hasNext();) {
				Article article = it.next();
				cal.setTime(article.getPostDate());
				int year = cal.get(Calendar.YEAR);
				if (year == i) {
					list.add(article);
					it.remove();
				}
			}
			countEachYear.put(i, list);
		}
		return countEachYear;
	}

	public Map<Integer, List<Article>> countByMonth(List<Article> yearList) {
		Calendar cal = Calendar.getInstance();
		
		Map<Integer, List<Article>> countEachMonth = new TreeMap<Integer, List<Article>>(new IntegerComparator());
		List<Article> temp = new ArrayList<Article>(yearList);
		for (int i = 11; i >= 0; i--) {
			List<Article> m_list = new ArrayList<Article>();
			for (Iterator<Article> it = temp.iterator(); it.hasNext();) {
				Article article = it.next();
				cal.setTime(article.getPostDate());
				int month = cal.get(Calendar.MONTH);
				if (month == i) {
					m_list.add(article);
					it.remove();
				}
			}
			countEachMonth.put(i, m_list);
		}
		return countEachMonth;
	}
	
	private class DateComparator implements Comparator<Article> {
		public int compare(Article o1, Article o2) {
			return o1.getPostDate().before(o2.getPostDate()) ? 1 : -1;
		}
	}
	private class IntegerComparator implements Comparator<Integer> {
		public int compare(Integer o1, Integer o2) {
			return o1 > o2 ? -1 : 1;
		}
	}
	
}
