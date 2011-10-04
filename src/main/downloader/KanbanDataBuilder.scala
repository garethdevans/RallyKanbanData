package downloader

import scala.xml._ 
import scala.collection.mutable.ListBuffer
import java.text.{SimpleDateFormat,DateFormat};
import java.util.Date;

class KanbanDataBuilder(
    val repository:Repository,
    val kanbanDataFilter:KanbanDataFilter,
    val revisionParser:RevisionParser,
    val dateParser:DateParser){	
	
	def this() = this(new Repository, new KanbanDataFilter, new RevisionParser, new DateParser)

	def build(project:Project, revisionHistoryUri:String):List[KanbanData] = {
		kanbanDataFilter.filter(getKanbanData(repository.getXml(project, revisionHistoryUri)))
	}

	private def getKanbanData(revisionsXml:Elem):List[KanbanData] = {
		var kanbanDataStates = for{
			r <- revisionsXml\"Revisions"\"Revision"
			if (hasStateData(r))
		} yield new KanbanData(parseDesciption(r), parseDate(r))
		kanbanDataStates.toList
	}	
		
	private def hasStateData(revision:Node):Boolean = {
	  getDescription(revision).contains("KANBANSTATE") || getDescription(revision).contains("READY changed from [false] to [true]") || getDescription(revision).contains("SCHEDULE STATE")
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
