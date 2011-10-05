package downloader

trait Revision {
  
	def hasData(entry:String):Boolean = {
		hasKanbanData(entry) && hasScrumData(entry) && hasTaskData(entry) && hasReadyData(entry) 
	}
	
	def hasKanbanData(entry:String):Boolean = {
	  entry.toUpperCase().contains("KANBANSTATE") || entry.toUpperCase().contains("DEVOKANBANSTATE") 
	}

	def hasScrumData(entry:String):Boolean = {
	  entry.toUpperCase().contains("SCHEDULE STATE")
	}

	def hasTaskData(entry:String):Boolean = {
	  entry.toUpperCase().contains("TASK ESTIMATE TOTAL CHANGED FROM [0.0]") 
	}

	def hasReadyData(entry:String):Boolean = {
	  entry.toUpperCase().contains("READY CHANGED FROM [FALSE] TO [TRUE]") 
	}	
}