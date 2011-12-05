package downloader

class ReadyStateConverter {
	def convert(kanbanStates:List[KanbanData]):List[KanbanData] = {
		converter(kanbanStates.sortWith((s,t) => s.date.getTime() < t.date.getTime()), List())
	}
	
	private def converter(in:List[KanbanData], out:List[KanbanData]):List[KanbanData] = {
	  if(in == Nil || in.head == Nil) return out.reverse	  
	  
	  if(in.head.state == "Ready" && out != Nil && out.head != Nil){
		  converter(in.tail, new KanbanData(createDoneState(out.head.state), in.head.date) :: out)
	  }
	  else{
		  converter(in.tail, in.head :: out)
	  }	  
	}
		
	private def createDoneState(state:String):String = {	
	  if (state.contains(" Done")) state else state + " Done"
	}
}