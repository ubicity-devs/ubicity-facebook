package at.ac.ait.ubicity.ubicity.facebook;

import java.util.List;

import org.junit.Test;

import at.ac.ait.ubicity.commons.util.PropertyLoader;
import at.ac.ait.ubicity.ubicity.facebook.impl.FBPluginImpl;
import at.ac.ait.ubicity.ubicity.facebook.impl.FbClient;

import com.restfb.Connection;
import com.restfb.Parameter;
import com.restfb.types.Page;
import com.restfb.types.Post;

public class FBTest {

	@Test
	public void ConnectionTest() {

		PropertyLoader config = new PropertyLoader(FBPluginImpl.class.getResource("/facebook.cfg"));

		FbClient client = new FbClient(config.getString("plugin.fb.api_key"), config.getString("plugin.fb.api_secret"));

		Page page = client.fetchObject("Starbucks", Page.class);

		System.out.println("Page likes: " + page.getLikes());

		client.renewAccessToken();

		Connection<Post> myFeed = client.fetchConnection("Starbucks/feed", Post.class);

		// paged list
		List<Post> posts = myFeed.getData();
		System.out.println("First item in my feed: " + posts.get(0));
	}

	@Test
	public void SearchTest() {
		PropertyLoader config = new PropertyLoader(FBPluginImpl.class.getResource("/facebook.cfg"));

		FbClient client = new FbClient(config.getString("plugin.fb.api_key"), config.getString("plugin.fb.api_secret"));

		Connection<Page> publicSearch = client.fetchConnection("search", Page.class, Parameter.with("q", "hochwasser"), Parameter.with("type", "page"));

		publicSearch.getData().forEach((entry) -> {
			System.out.println(entry.getName() + "-" + entry.getAbout());
		});
	}
}