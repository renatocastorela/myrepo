package org.oiga.vertex.credentials;

public class FoursquareApiCredential {
	private  String clientId = "M3XC102K5CRRC4OZLGQTXWICDAQUCB2MWQY0Q1WADK2STNMU";
	private  String clientSecret = "EEVCZRRIOTLRJM5Z1JUXQVJ2L3RWLZ53D3YSOH1ZAI221ZQL";
	private  String version = "20131014";
	
	public static final String _CLIENT_ID_PARAM = "client_id";
	public static final String _CLIENT_SECRET_PARAM = "client_secret";
	public static final String _VERSION_PARAM = "v";
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
