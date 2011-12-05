package downloader

import scala.xml._ 
import scala.collection.mutable.ListBuffer
import java.util.Date

class StoryBuilder(
    val repository:Repository,
    val kanbanDataBuilder:KanbanDataBuilder,
    val dateParser:DateParser){
	private val configManager = new ConfigManager 

	def this() = this(new Repository, new KanbanDataBuilder, new DateParser)
	
	def build(project:Project, page:Int):List[Story]={
		val stories = getStories(project, repository.getXml(project, configManager.storiesUri, page))
		println("----------" + project.name)
		printAndReturnStories(stories)		
	}
	
	private def printAndReturnStories(stories:List[Story]) = {
		stories foreach{(s) =>
		  println(s.id, s.name, s.release, s.storyType, s.hasChildren, printStates(s.kanbanStates))
		}
		stories
	}
	
	private def printStates(kanbanStates:List[KanbanData]):String = {
	  var states = ""
	  kanbanStates foreach{(s) =>
	    states += s.state + "," + s.date + "|"
	  }
	  states
	}
	
	private def getStories(project:Project, storiesXml:Elem):List[Story] = {	  
		var stories = for{
			storyXml <- storiesXml\"Results"\"Object"		    
			val s = repository.getXml(project, getStoryUri(storyXml))
		} yield new Story(getId(s), getParentId(project, s), getName(s), getStoryUri(storyXml), getStoryType(s), getRelease(s), hasChildren(s), getCreationDate(s), getKanbanData(project, s))
		return stories.toList
	}
	
	private def getParentId(project:Project, s:Elem):String = {
	  if (getParentUri(s) == "") return "" else getId(repository.getXml(project, getParentUri(s)))
	}
	
	private def getParentUri(s:Elem):String = {
	  (s\"Parent"\"@ref").text
	}
	
	private def getKanbanData(project:Project, story:Elem):List[KanbanData] = {
	  kanbanDataBuilder.build(project, getRevisionHistoryUri(story))
	}

	private def hasChildren(s:Elem):Boolean= {
	  (s\"Children"\"HierarchicalRequirement").length > 0
	}

	private def getStoryUri(storyXml:Node):String= {
	  (storyXml\"@ref").text
	}
	
	private def getId(s:Elem):String = {
		(s\"FormattedID").text
	}	

	private def getName(s:Elem):String = {
		(s\"Name").text.replaceAll(",", ";")
	}	
		
	private def getRevisionHistoryUri(s:Elem):String = {
		(s\"RevisionHistory"\"@ref").text
	}	
	
	private def getStoryType(s:Elem):String = {
		(s\"StoryType").text
	}	
	
	private def getRelease(s:Elem):String = {
	  (s\"Release"\"@refObjectName").text.replaceAll(",", ";")
	}

	private def getCreationDate(s:Elem):Date = {
		dateParser.parse((s\"CreationDate").text)
	}	

}
