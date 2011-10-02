package downloader

import java.net.{URLConnection, URL}
import scala.xml._ 
import sun.misc.BASE64Decoder;

class Repository{		
	def getXml(project:Project, resource:String):Elem = {
		get(new URL(resource + "?project=" + project.uri), project.username, project.password)
	}
  
	def getXml(project:Project, resource:String, page:Int):Elem = {
		get(new URL(resource + "?project=" + project.uri + "&start=" + page), project.username, project.password)
	}
	
	private def get(url:URL, username:String, password:String):Elem = {
		val conn = url.openConnection
		val input = username + ":" + password
		val encoding = new sun.misc.BASE64Encoder().encode(input.getBytes)
		conn.setRequestProperty("Authorization", "Basic " + encoding)
		val xml = XML.load(conn.getInputStream)
		println(xml)
		xml
	}
}
