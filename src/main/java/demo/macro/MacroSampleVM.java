package demo.macro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

public class MacroSampleVM {
	private DefaultTreeModel<ArticleLabel> menuModel;
	private ArticleDAO articleDao = ArticleDAO.getInstance();
	private Article article;
	private TreeNode<ArticleLabel> path;
	private boolean articleView = false, articlesView = true;

	public MacroSampleVM() {
		TreeNode<ArticleLabel> root = new DefaultTreeNode<ArticleLabel>(null, generateYears());
		menuModel = new DefaultTreeModel<ArticleLabel>(root);
		TreeNode<ArticleLabel> firstLeaf = getFirstLeaf(menuModel.getRoot());
		path = firstLeaf.getParent();
		menuModel.clearOpen();
		openNode(path);
		menuModel.addToSelection(path);
		ArticleLabel aLabel = path.getData();
		article = aLabel.getArticle();
	}

	public DefaultTreeModel<ArticleLabel> getMenuModel() {
		return menuModel;
	}

	public TreeNode<ArticleLabel> getPath() {
		return path;
	}

	public Article getArticle() {
		return article;
	}

	public boolean isArticleView() {
		return articleView;
	}

	public boolean isArticlesView() {
		return articlesView;
	}

	@Command("loadArticle1")
	@NotifyChange({"path", "article", "articles", "articleView", "articlesView"})
	public void loadArticle(@BindingParam("node") DefaultTreeNode<ArticleLabel> node) {
		path = node;
		menuModel.addOpenObject(path);
		menuModel.addToSelection(path);
		ArticleLabel aLabel = node.getData();
		article = aLabel.getArticle();
		articleView = article != null;
		articlesView = !articleView;
	}

	@Command("loadArticle2")
	@NotifyChange({"path", "article", "articles", "articleView", "articlesView"})
	public void loadArticle(@BindingParam("node") DefaultTreeNode<ArticleLabel> node, @BindingParam("id") int id) {
		path = getLeaf(node, id);
		article = path.getData().getArticle();
		menuModel.clearOpen();
		openNode(path);
		menuModel.addToSelection(path);
		articleView = true;
		articlesView = false;
	}

	private List<TreeNode<ArticleLabel>> generateYears() {
		Map<Integer, List<Article>> countEachYear = articleDao.countByYear();
		
		List<TreeNode<ArticleLabel>> years = new ArrayList<TreeNode<ArticleLabel>>();
		for (Entry<Integer, List<Article>> entry : countEachYear.entrySet()) {
			List<Article> list = entry.getValue();
			if (!list.isEmpty()) {
				ArticleLabel al = new ArticleLabel(entry.getKey() + " (" + list.size() + ")", null, list);
				years.add(new DefaultTreeNode<ArticleLabel>(al, generateMonths(list)));
			}
		}
		return years;
	}

	private List<TreeNode<ArticleLabel>> generateMonths(List<Article> yearList) {
		String[] monthLabel = {
				"January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December"
			};
		
		Map<Integer, List<Article>> countEachMonth = articleDao.countByMonth(yearList);
		
		List<TreeNode<ArticleLabel>> months = new ArrayList<TreeNode<ArticleLabel>>();
		for (Entry<Integer, List<Article>> entry : countEachMonth.entrySet()) {
			List<Article> list = entry.getValue();
			if (!list.isEmpty()) {
				ArticleLabel al = new ArticleLabel(monthLabel[entry.getKey()] + " (" + list.size() + ")", null, list);
				months.add(new DefaultTreeNode<ArticleLabel>(al, generateArticles(list)));
			}
		}

		return months;
	}

	private List<TreeNode<ArticleLabel>> generateArticles(List<Article> list) {
		List<TreeNode<ArticleLabel>> articles = new ArrayList<TreeNode<ArticleLabel>>();
		for (Article article : list)
			articles.add(new DefaultTreeNode<ArticleLabel>(new ArticleLabel(article.getTitle(), article, null)));

		return articles;
	}

	private void openNode(TreeNode<ArticleLabel> node) {
		menuModel.addOpenObject(node);
		TreeNode<ArticleLabel> parent = node.getParent();
		if (parent != null) {
			openNode(parent);
		}
	}

	private TreeNode<ArticleLabel> getFirstLeaf(TreeNode<ArticleLabel> root) {
		TreeNode<ArticleLabel> child = root.getChildAt(0);
		if (!child.isLeaf())
			child = getFirstLeaf(child);
		
		return child;
	}

	private TreeNode<ArticleLabel> getLeaf(TreeNode<ArticleLabel> node, int id) {
		TreeNode<ArticleLabel> leaf = null;
		if (node.isLeaf()) {
			Article a = node.getData().getArticle();
			if (a.getId() == id)
				leaf = node;
			return leaf;
		} else {
			for (TreeNode<ArticleLabel> n : node.getChildren()) {
				leaf = getLeaf(n, id);
				if (leaf != null)
					break;
			}
		}
		return leaf;
	}
}
