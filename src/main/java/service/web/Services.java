package service.web;

import com.google.gson.Gson;
import ir.jm.iranpostcode.model.dto.entity.AutocompleteRes;
import ir.jm.iranpostcode.model.dto.entity.AvensRes;
import ir.jm.iranpostcode.model.dto.entity.BuildsRes;
import ir.jm.iranpostcode.model.dto.entity.Data;
import ir.jm.iranpostcode.model.dto.entity.Datum;
import ir.jm.iranpostcode.model.dto.entity.PostCodeRes;
import ir.jm.iranpostcode.model.dto.entity.Suggestion;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Jafar Mirzaei (jm.csh2009@gmail.com)
 * @version 1.0 2018.0204
 * @since 1.0
 */
public final class Services {
  private static final String session = "DGhRJWHUXf9JLFhlD2OKg2Z2s9gTJY";
  public static final String autoCompleteUrl =
      "https://epostcode.post.ir/api/" + session + "/autoCompelete";
  public static final String getAvensUrl =
      "https://epostcode.post.ir/api/" + session + "/getAvens";
  public static final String getBuildingsUrl =
      "https://epostcode.post.ir/api/" + session + "/buildings";
  public static final String getPostCodeUrl =
      "https://epostcode.post.ir/api/" + session + "/getPostCode";
  public static final String COOCKIE =
      "ST=" + session;
  static CloseableHttpClient httpclient;

  static int houseNum = 53;

  public Services() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    SSLContextBuilder builder = new SSLContextBuilder();
    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
        builder.build());
    this.httpclient = HttpClients.custom().setSSLSocketFactory(
        sslsf).build();

  }

  public AutocompleteRes getAutoComplete(final int houseNum, final String query) throws KeyStoreException, NoSuchAlgorithmException,
                                                                                        KeyManagementException, IOException {


    HttpPost httpPost = new HttpPost(autoCompleteUrl);
    List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
    urlParameters.add(new BasicNameValuePair("loc_id", "99999"));
    urlParameters.add(new BasicNameValuePair("catalog", "aven"));
    urlParameters.add(new BasicNameValuePair("parish_id", "0"));
    urlParameters.add(new BasicNameValuePair("area", "18"));
    urlParameters.add(new BasicNameValuePair("house_no", houseNum + ""));
    urlParameters.add(new BasicNameValuePair("q", query));

    HttpEntity postParams = new UrlEncodedFormEntity(urlParameters, "UTF-8");
    httpPost.setEntity(postParams);
    httpPost.addHeader("Cookie", COOCKIE);
    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    AutocompleteRes parse = new AutocompleteRes();
    parse.setSuggestions(new HashSet<>());
    CloseableHttpResponse response = httpclient.execute(httpPost);
    try {
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        if (entity.isStreaming()) {
          InputStream instream = entity.getContent();
          String json = EntityUtils.toString(response.getEntity());
          Gson gson = new Gson();
          parse = gson.fromJson(json, AutocompleteRes.class);
          return parse;
        }
      }
      EntityUtils.consume(entity);
    } finally {
      response.close();
    }
    return parse;
  }

  public AvensRes getAvens(final Suggestion suggestion) throws KeyStoreException, NoSuchAlgorithmException,
                                                               KeyManagementException, IOException, URISyntaxException {
    URI uri = new URIBuilder(getAvensUrl)
        .addParameter("unit", "0816")
        .addParameter("loc_id", "99999")
        .addParameter("parish_id", suggestion.getData().substring(suggestion.getData().indexOf("-") + 1))
        .addParameter("aven_id", suggestion.getData().substring(0, suggestion.getData().indexOf("-")))
        //    .addParameter("_",suggestion.getData().substring(0,suggestion.getData().indexOf("-")))
        .build();

    HttpGet httpGet = new HttpGet(uri);

    httpGet.addHeader("Cookie", COOCKIE);
    httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    AvensRes parse = new AvensRes();
    parse.setData(new HashSet<>());
    CloseableHttpResponse response = httpclient.execute(httpGet);
    try {
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        if (entity.isStreaming()) {
          String json = EntityUtils.toString(response.getEntity());
          Gson gson = new Gson();
          parse = gson.fromJson(json, AvensRes.class);
          return parse;
        }
      }
      EntityUtils.consume(entity);
    } finally {
      response.close();
    }
    return parse;
  }

  public BuildsRes getBuilds(final Datum datum, final Suggestion suggestion) throws KeyStoreException,
                                                                                    NoSuchAlgorithmException,
                                                                                    KeyManagementException, IOException,
                                                                                    URISyntaxException {
    URI uri = new URIBuilder(getBuildingsUrl)
        .addParameter("unit", "0816")
        .addParameter("loc_id", "99999")
        .addParameter("parish_id", suggestion.getData().substring(suggestion.getData().indexOf("-") + 1))
        .addParameter("aven_id", suggestion.getData().substring(0, suggestion.getData().indexOf("-")))
        .addParameter("pre_aven_id", datum.getPreAvenId() + "")
        //    .addParameter("_",suggestion.getData().substring(0,suggestion.getData().indexOf("-")))
        .build();

    HttpGet httpGet = new HttpGet(uri);

    httpGet.addHeader("Cookie", COOCKIE);
    httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    BuildsRes parse = new BuildsRes();
    parse.setData(new HashSet<>());
    CloseableHttpResponse response = httpclient.execute(httpGet);
    try {
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        if (entity.isStreaming()) {
          String json = EntityUtils.toString(response.getEntity());
          Gson gson = new Gson();
          parse = gson.fromJson(json, BuildsRes.class);
          return parse;
        }
      }
      EntityUtils.consume(entity);
    } finally {
      response.close();
    }
    return parse;
  }

  public PostCodeRes getPostCode(final Data data) throws KeyStoreException, NoSuchAlgorithmException,
                                                         KeyManagementException, IOException, URISyntaxException {
    URI uri = new URIBuilder(getPostCodeUrl)
        .addParameter("rowguid", data.getRowguid())
        //    .addParameter("_",suggestion.getData().substring(0,suggestion.getData().indexOf("-")))
        .build();

    HttpGet httpGet = new HttpGet(uri);

    httpGet.addHeader("Cookie", COOCKIE);
    httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    PostCodeRes parse = new PostCodeRes();

    CloseableHttpResponse response = httpclient.execute(httpGet);
    try {
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        if (entity.isStreaming()) {
          String json = EntityUtils.toString(response.getEntity());
          Gson gson = new Gson();
          parse = gson.fromJson(json, PostCodeRes.class);
          return parse;
        }
      }
      EntityUtils.consume(entity);
    } finally {
      response.close();
    }
    return parse;
  }

}

