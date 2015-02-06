package at.ac.ait.ubicity.ubicity.facebook.impl;

import com.restfb.DefaultFacebookClient;
import com.restfb.Version;

public class FbClient extends DefaultFacebookClient {

	private final String appId;
	private final String appSecret;

	public FbClient(String appId, String appSecret) {
		super("", Version.VERSION_2_2);
		this.appId = appId;
		this.appSecret = appSecret;

		renewAccessToken();
	}

	/**
	 * Renews the access token.
	 */
	public void renewAccessToken() {
		this.accessToken = this
				.obtainAppAccessToken(this.appId, this.appSecret)
				.getAccessToken();
	}
}
