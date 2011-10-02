package downloader

import scala.xml._ 
import scala.collection.mutable.ListBuffer

class StoryBuilder(
    val repository:Repository,
    val kanbanDataBuilder:KanbanDataBuilder){
	private val configManager = new ConfigManager 

	def this() = this(new Repository, new KanbanDataBuilder)
	
	def build(project:Project, page:Int):List[Story]={
		getStories(project, repository.getXml(project, configManager.storiesUri, page))
	}
	
	private def getStories(project:Project, storiesXml:Elem):List[Story] = {	  
		var stories = for{
			storyXml <- storiesXml\"Results"\"Object"		    
			val s = repository.getXml(project, getStoryUri(storyXml))
		} yield new Story(getId(s), getName(s), getStoryUri(storyXml), getStoryType(s), getKanbanData(project, s))
		return stories.toList
	}
	
	private def getKanbanData(project:Project, story:Elem):List[KanbanData] = {
	  kanbanDataBuilder.build(project, getRevisionHistoryUri(story))
	}

	private def getStoryUri(storyXml:Node):String= {
	  (storyXml\"@ref").text
	}
	
	private def getId(s:Elem):String = {
		(s\"FormattedID").text
	}	

	private def getName(s:Elem):String = {
		(s\"Name").text
	}	
		
	private def getRevisionHistoryUri(s:Elem):String = {
		(s\"RevisionHistory"\"@ref").text
	}	
	
	private def getStoryType(s:Elem):String = {
		(s\"StoryType").text
	}	
}
