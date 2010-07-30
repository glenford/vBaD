package net.usersource.http.simple

import org.apache.commons.httpclient.auth.AuthScope
import org.apache.commons.httpclient.methods.GetMethod
import org.apache.commons.httpclient.{DefaultHttpMethodRetryHandler, HttpClient, UsernamePasswordCredentials}
import org.apache.commons.httpclient.params.HttpMethodParams



class Response(val status: Int, val headers: Map[String,String], val body: Option[String])



object Http {

  private def asOption[T]( t: T ):Option[T] = {
    if(t == null) None else Some(t)
  }

  private def extractHostAndPort( url: String ): (String, Int) = {
    val stripped = url.stripPrefix("http://")
    val hostAndPort = stripped.substring( 0, stripped.findIndexOf( c => c == '/') )
    if( hostAndPort.findIndexOf(c => c == ':') < 0 ) (hostAndPort, 80)
    else (hostAndPort.substring(0,hostAndPort.findIndexOf(c => c == ':')), Integer.parseInt(hostAndPort.substring(hostAndPort.findIndexOf(c => c == ':')+1)) )
  }

  private def createGetMethod(path:String):GetMethod = {
    val method = new GetMethod(path)
    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
    method.setRequestHeader("User-Agent", "net.usersource.http.simple");
    method
  }

  private def get( httpClient: HttpClient, url: String ): Response = {
    val method = createGetMethod(url)
    try {
      val status = httpClient.executeMethod(method)
      val mapBuilder = Map.newBuilder[String,String]
      val headers = method.getResponseHeaders.foreach( (h) => { mapBuilder += {( h.getName, h.getValue )} } )
      val body = method.getResponseBodyAsString
      new Response( status, mapBuilder.result, asOption(body) )
    }
    finally {
        method.releaseConnection()
    }
  }

  def get( url: String ): Response = {
    val httpClient = new HttpClient
    get(httpClient, url)
  }

  def get( url:String, username: String, password: String ): Response = {
    val httpClient = new HttpClient
    val defaultcreds = new UsernamePasswordCredentials(username, password);
    val (host,port) = extractHostAndPort(url)
    httpClient.getState().setCredentials(new AuthScope( host, port, AuthScope.ANY_REALM), defaultcreds);
    get(httpClient, url)
  }

}