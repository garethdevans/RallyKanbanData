package downloader

class KanbanStateMatcher(blankDateFiller:BlankDateFiller) {
  
	def this() = this(new BlankDateFiller())
	
	def matchStates(stateNames:List[String], kanbanStates:List[KanbanData]):List[KanbanData] = {
		var states = for {
		  s <- stateNames		  
		} yield getState(kanbanStates, s)
		states.foreach(s=>println(s.state, s.date))
		blankDateFiller.fill(stateNames, states.toList) 
	}

	private def getState(kanbanStates:List[KanbanData], state:String):KanbanData = {
		if(kanbanStates.exists(x=>x.state.trim.toUpperCase() == state.trim.toUpperCase())){
		  findState(kanbanStates, state)
		}
		else new KanbanData(state, null)
	}
	
	private def findState(kanbanStates:List[KanbanData], state:String):KanbanData = {	  
		kanbanStates.filter(x => x.state.trim.toUpperCase() == state.trim.toUpperCase()).sortWith((s,t) => s.date.getTime() > t.date.getTime()).head  	  	  
	}
	
}