package demo.macro;

import java.util.LinkedList;

import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.TreeNode;

public class BreadCrumb extends HtmlMacroComponent {
	private static final long serialVersionUID = -8865508465384703560L;
	
	@Wire
	private Hlayout m_hlayout;
	private TreeNode<ArticleLabel> _node;
	private LinkedList<TreeNode<ArticleLabel>> _paths = new LinkedList<TreeNode<ArticleLabel>>();
	
	public BreadCrumb() {
		compose();
	}
	
	public TreeNode<ArticleLabel> getPath() {
		return _node;
	}
	
	public void setPath(TreeNode<ArticleLabel> node) {
		_node = node;
		_paths.clear();
		m_hlayout.getChildren().clear();
		generatePaths(node);
		A[] links = new A[_paths.size() - 1];
		Label[] separators = new Label[_paths.size() - 1];
		
		for (int i = links.length - 1; i >= 0; i--) {
			TreeNode<ArticleLabel> n = _paths.pollLast();
			String label = n.getData().getLabel();
			label = label.replaceAll("\\s\\((\\d+)\\)", "");
			links[i] = new A(label);
			links[i].setParent(m_hlayout);
			links[i].setSclass("text");
			links[i].addForward(Events.ON_CLICK, this, "onPathClick", n);
			separators[i] = new Label(">");
			separators[i].setParent(m_hlayout);
			separators[i].setSclass("text divider");
		}
		String label = _paths.pollFirst().getData().getLabel();
		label = label.replaceAll("\\s\\((\\d+)\\)", "");
		Label last = new Label(label);
		last.setParent(m_hlayout);
		last.setSclass("text");
		_paths.clear();
	}
	
	private LinkedList<TreeNode<ArticleLabel>> generatePaths(TreeNode<ArticleLabel> node) {
		ArticleLabel data = node.getData();
		if (data != null)
			_paths.add(node);
		
		DefaultTreeNode<ArticleLabel> parent = (DefaultTreeNode<ArticleLabel>) node.getParent();
		if (parent != null) {
			return generatePaths(parent);
		}
		return _paths;
	}
}
