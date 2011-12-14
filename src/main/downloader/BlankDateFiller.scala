package downloader

import java.util.Date
import scala.collection.mutable.ListBuffer;

class BlankDateFiller {
  
	def fill(stateNames:List[String], kanbanStates:List[KanbanData]):List[KanbanData] = {	  	  	  
	  var states = new ListBuffer[KanbanData]()
	  var maxDate:Date = null
	  var maxPreviousDate:Date = null
	  
	  stateNames.reverse foreach({ name =>
	  	maxDate = getMaxDateForState(name, kanbanStates)
	    for{
	      s <- kanbanStates.filter(x => x.state.trim.toUpperCase() == name.trim.toUpperCase())
	    } yield if (maxDate == null && maxPreviousDate !=null) states.append(fixKanbanDate(maxPreviousDate, s)) else states.append(fixKanbanDate(maxDate, s))
		maxPreviousDate = maxDate
	  })  
	  
	  states.toList.reverse
	}

	def getMaxDateForState(state:String, kanbanStates:List[KanbanData]):Date = {
		if (kanbanStates.exists(x => x.state == state && x.date != null)) findMaxDateForState(state, kanbanStates) else null
	}

	def findMaxDateForState(state:String, kanbanStates:List[KanbanData]):Date = {	  
		kanbanStates.filter(x => x.state == state && x.date != null).sortWith((s,t) => s.date.getTime() > t.date.getTime()).head.date
	}
		
	def fixKanbanDate(date:Date, kanbanData:KanbanData):KanbanData = {	    
	    if (kanbanData.date == null) new KanbanData(kanbanData.state, date) else kanbanData
	}
}