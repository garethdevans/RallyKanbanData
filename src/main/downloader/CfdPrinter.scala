package downloader

import java.util.Date
import java.io.PrintWriter
import java.io.File

class CfdPrinter {
	def toCfd(project:Project) = {
	  withPrintWriter(new File("data/" + project.name + "_CFD.csv")){
		  writer =>
		    var states:String = ""
		    project.kanbanStates foreach{(x) =>		      		    
		    	for(d <- 1000 until 30000){
		    		//states += project.stories.sum(s=>s.kanbanStates.count(k => k.state.trim.toUpperCase == x.trim.toUpperCase() && k.date.getTime() >= d))
		    		states += ","
		    	}
		    }
		    writer.println("Id,Name,Story Type,Release," + project.kanbanStates.reduceLeft(_ + "," + _))
	  }
	}
	
	private def stateForDateRangeCounter(startDate:Int, endDate:Int, story:Story):Int = {
	  var total:Int = 0
	  story.kanbanStates foreach{(k) =>
	    if (k.date.getTime() >= startDate && k.date.getTime() < endDate) total += 1
	  }
	  total
	}
	
	private def withPrintWriter(file:File)(op : PrintWriter =>  Unit){
		val writer = new PrintWriter(file)
		try{
		  op(writer)
		}finally{
		  writer.close()
		}		
	}

}