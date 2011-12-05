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
		withPrintWriter(new File("data/" + name + ".csv")){
			writer => 
		    	writer.println("Id,ParentId,Name,Story Type,HasChildren,Release,CreationDate," + kanbanStates.reduceLeft(_ + "," + _))
		    	stories foreach{(s) =>
		    		writer.println(s.id + "," + s.parentId + "," + s.name + "," + s.storyType + "," + s.hasChildren + "," + s.release + "," + format(s.creationDate) + "," + printStates(s))
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
	
	private def printStates(story:Story):String = {
	  var states = for {
		  k <- story.kanbanStates
	  } yield format(k.date)
	  states.reduceLeft(_ + "," + _)
	}
	
	private def format(date:Date):String = {
	  if (date == null) "" else new SimpleDateFormat("dd/MM/yyyy").format(date)
	}
}