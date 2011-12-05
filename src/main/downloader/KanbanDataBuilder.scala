package downloader

import scala.xml._ 
import scala.collection.mutable.ListBuffer
import java.text.{SimpleDateFormat,DateFormat};
import java.util.Date;

class KanbanDataBuilder(
    val repository:Repository,
    val kanbanStateMatcher:KanbanStateMatcher,
    val revisionParser:RevisionParser,
    val dateParser:DateParser) extends Revision{	
	
	def this() = this(new Repository, new KanbanStateMatcher, new RevisionParser, new DateParser)

	def build(project:Project, revisionHistoryUri:String):List[KanbanData] = {
		val k = getKanbanData(repository.getXml(project, revisionHistoryUri))		
		val states = kanbanStateMatcher.matchStates(project, k)
		states.foreach(s=>println(s.state, s.date))
		states
	}

	private def getKanbanData(revisionsXml:Elem):List[KanbanData] = {
		var kanbanDataStates = for{
			r <- revisionsXml\"Revisions"\"Revision"
			if (hasData(getDescription(r)))
		} yield new KanbanData(parseDesciption(r), parseDate(r))
		kanbanDataStates.toList
	}	
			
	private def getDescription(revision:Node):String = {
	  (revision\"Description").text
	}

	private def parseDesciption(revision:Node):String = {
	  revisionParser.parse(getDescription(revision))
	}

	private def parseDate(revision:Node):Date = {
	  dateParser.parse((revision\"CreationDate").text)
	}	

}
