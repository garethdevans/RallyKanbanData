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
	val csvPrinter = new CsvPrinter()
	
	def toCsv = {
	  csvPrinter.print(name + "_feature", stories.filter(x => x.hasChildren == true), kanbanStates)
	  csvPrinter.print(name + "_story", stories.filter(x => x.hasChildren == false), kanbanStates)
	}
	
}