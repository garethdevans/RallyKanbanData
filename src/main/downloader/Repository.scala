package downloader

import java.net.{URLConnection, URL}
import scala.xml._ 
import sun.misc.BASE64Decoder;

class Repository{		
	var retryCounter:Int = 0
	
	def getXml(project:Project, resource:String):Elem = {
	    retryCounter = 0
	    val u = resource + "?project=" + project.uri
	    println(u)
		get(new URL(u), project.username, project.password)
	}
  
	def getXml(project:Project, resource:String, page:Int):Elem = {
	    retryCounter = 0
	    val u = resource + "?project=" + project.uri + "&start=" + page
	    println(u)	    
		get(new URL(u), project.username, project.password)
	}
	
	private def get(url:URL, username:String, password:String):Elem = {
	  try{
		val conn = url.openConnection
		val input = username + ":" + password
		val encoding = new sun.misc.BASE64Encoder().encode(input.getBytes)
		conn.setRequestProperty("Authorization", "Basic " + encoding)
		val xml = XML.load(conn.getInputStream)
		xml
	  }
	  catch{	    
	    case e:java.io.IOException => {
	        retryCounter = retryCounter + 1
	    	if(retryCounter < 3) get(url, username, password) else{ throw e }
	    }
	    case e => { throw e}
	  }
	}
}
