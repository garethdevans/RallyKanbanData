package downloader

class RevisionParser{
	def parse(revision:String):String = {
		revision.split(",") foreach {(entry) => 
			if (entry.contains("KANBANSTATE") || entry.contains("SCHEDULE STATE")){
				return entry.split('[').last.dropRight(1)
			}
			if (entry.contains("Ready changed from [false] to [true]")){
				return "Ready"
			}
		}
		
		throw new IllegalArgumentException("revision did not contain Kanban data.")
	}
}
