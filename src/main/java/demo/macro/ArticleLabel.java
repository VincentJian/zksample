package demo.macro;

import java.util.List;

public class ArticleLabel {
	private String _label;
	private Article _article;
	private List<Article> _articles;
	
	public ArticleLabel(String label, Article article, List<Article> articles) {
		_label = label;
		_article = article;
		_articles = articles;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public Article getArticle() {
		return _article;
	}

	public void setArticle(Article article) {
		_article = article;
	}

	public List<Article> getArticles() {
		return _articles;
	}

	public void setArticles(List<Article> articles) {
		_articles = articles;
	}
}
