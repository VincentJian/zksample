package demo.macro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Article {
	private int _id;
	private Date _postDate;
	private String _title;
	private String[] titles = {
			"Lorem ipsum dolor si",
			"Sed ut perspiciatis.",
			"But I must explain t",
			"Li Europan lingues e",
			"The European languag", 
			"Far far away, behind",
			"A wonderful serenity",
			"One morning, when Gr",
			"The quick, brown fox"
		};
	private String _content;
	private String[] contents = {
			"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.\n\n" +
					"Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec qu",
			"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, " + 
					"eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta.",
			"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a " + 
					"complete account of the system, and expound the actual teachings of the gre",
			"Li Europan lingues es membres del sam familie. Lor separat existentie es un myth.\n\n" +
					"Por scientie, musica, sport etc, litot Europa usa li sam vocabular. Li lingues differe solmen in li grammatica, li pro",
			"The European languages are members of the same family. Their separate existence is a myth.\n\n" +
					"For science, music, sport, etc, Europe uses the same vocabulary. The languages only differ in their grammar,.",
			"Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.\n\n" +
					"Separated they live in Bookmarksgrove right at the coast of the Semantics, a large.",
			"A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart.\n\n" +
					"I am alone, and feel the charm of existence in this spot, which was.",
			"One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin.\n\n" +
					"He lay on his armour-like back, and if he lifted his head a little he could se",
			"The quick, brown fox jumps over a lazy dog. DJs flock by when MTV ax quiz prog.\n\n" +
					"Junk MTV quiz graced by fox whelps. Bawds jog, flick quartz, vex nymphs. Waltz, bad nymph, for quick jigs vex! Fox nymph"
		};
	private List<String> _tags;
	
	private Random rand = new Random();
	
	public Article(int id) {
		_id = id;
		Calendar cal = Calendar.getInstance();
		long end = cal.getTimeInMillis();
		cal.set(2010, 0, 0);
		long begin = cal.getTimeInMillis();
		_postDate = new Date(begin + (long)(rand.nextDouble() * (end - begin + 1)));
		_title = titles[rand.nextInt(titles.length)];
		_content = contents[rand.nextInt(contents.length)];
		_tags = new ArrayList<String>();
	}

	public int getId() {
		return _id;
	}
	
	public void setId(int id) {
		_id = id;
	}
	
	public Date getPostDate() {
		return _postDate;
	}

	public void setPostDate(Date postDate) {
		_postDate = postDate;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_content = content;
	}

	public String getBriefContent() {
		return _content.substring(0, 100);
	}

	public List<String> getTags() {
		return _tags;
	}

	public void setTags(List<String> tags) {
		_tags = tags;
	}
}
