package downloader

import scala.collection.mutable.{Queue,HashMap,ListBuffer,Stack}
import scala.xml._ 

class ConfigManager(
      val configFile:String){
    
    def this() = this("config.xml")
    
	def projects:List[Project] = {
		val projects = 
			for {
				p <- XML.loadFile(configFile)\"project"
			} yield new Project((p\"name").text, (p\"uri").text, (p\"kanbanStates").text.split(',').toList, (p\"username").text, (p\"password").text)
		projects.toList
	}
	
	val storiesUri:String = "https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement"
}