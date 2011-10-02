package downloader
 
import java.util.Date
import java.io.PrintWriter
import java.io.File
import java.text.{SimpleDateFormat,DateFormat};

class Project(
	val name:String, 
	val uri:String, 
	val kanbanStates:List[String],
	val username:String,
	val password:String){
	var stories: List[Story] = List()
		
	def toCsv = {
		withPrintWriter(new File(name + ".csv")){
		  writer => 
		    writer.println("Id,Name,Story Type," + kanbanStates.reduceLeft(_ + "," + _))
		    stories foreach{(s) =>
		      writer.println(s.id + "," + s.name + "," + s.storyType + "," + kanbanDatesFor(s))
			}
		}
	}
	
	private def withPrintWriter(file:File)(op : PrintWriter =>  Unit){
		val writer = new PrintWriter(file)
		try{
		  op(writer)
		}finally{
		  writer.close()
		}		
	}
	
	private def kanbanDatesFor(story:Story):String = {
		var line = for {
		  k <- kanbanStates		  
		} yield findState(story, k)
		line.reduceLeft(_ + "," + _)
	}

	private def findState(story:Story, state:String):String = {
	  if(story.kanbanStates.exists(x => x.state == state)) format(story.kanbanStates.filter(x => x.state == state).head.date) else ""
	}
	
	private def format(date:Date):String = {
	  new SimpleDateFormat("dd/MM/yyyy").format(date)
	}
}