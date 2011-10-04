package downloader

import scala.collection.mutable.ListBuffer

class KanbanDataFilter(
    val readyStateConverter:ReadyStateConverter){
  
	def this() = this(new ReadyStateConverter)
  
	def filter(kanbanStates:List[KanbanData]):List[KanbanData] = {		
		filterStates(readyStateConverter.convert(kanbanStates))
	}
	
	private def filterStates(kanbanStates:List[KanbanData]):List[KanbanData] = {
		var filteredStates = new ListBuffer[KanbanData]
		kanbanStates foreach {(k) =>
			if(!filteredStates.exists(x=>x.state == k.state)){
				filteredStates += kanbanStates.filter(x => x.state == k.state).sortWith((s,t) => s.date.getTime() > t.date.getTime()).head
			}
		}
		filteredStates.toList
	}
}
