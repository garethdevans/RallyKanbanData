package downloader

class RevisionParser extends Revision{
	def parse(revision:String):String = {
		revision.split(",") foreach {(entry) => 
			if (hasKanbanData(entry) || hasScrumData(entry)){
				return entry.split('[').last.trim.dropRight(1)
			}
			if (hasReadyData(entry)){
				return "Ready"
			}
			if (hasTaskData(entry)){
				return "Defined"
			}
		}
		
		throw new IllegalArgumentException("revision did not contain Kanban data. " + revision.toUpperCase())
	}
}
