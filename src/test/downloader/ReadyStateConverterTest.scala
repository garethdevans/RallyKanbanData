package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import java.util.Date

class ReadyStateConverterTest extends FlatSpec with ShouldMatchers{
  	"convert" should "return an empty list when an empty list is passed in" in {
  		new ReadyStateConverter().convert(List()).length should be (0)
  	}
  	
	"convert for a single state and multiple ready state" should "change ready state to previous state with Done appended to end" in {
		val readyStateConverter = new ReadyStateConverter
		val kanbanStates = List(new KanbanData("Dev",new Date(1)), new KanbanData("Ready",new Date(2)), new KanbanData("Ready",new Date(3)))
		var kanbanDataStates = readyStateConverter.convert(kanbanStates)
		kanbanDataStates.length should be (3)
		kanbanDataStates.count(x => x.state == "Dev" && x.date.getTime() == 1) should be (1)
		kanbanDataStates.count(x => x.state == "Dev Done" && x.date.getTime() == 2) should be (1)
		kanbanDataStates.count(x => x.state == "Dev Done" && x.date.getTime() == 3) should be (1)
		print(kanbanDataStates)
	}
	
	"convert for multiple states with multiple ready states" should "change ready state to previous state with Done appended to end" in {
		val readyStateConverter = new ReadyStateConverter
		val kanbanStates = List(
		    new KanbanData("Dev", new Date(1)), 
		    new KanbanData("Ready", new Date(2)), 
		    new KanbanData("Test", new Date(3)), 
		    new KanbanData("Ready", new Date(4)),
		    new KanbanData("Ready", new Date(5)),
		    new KanbanData("Complete", new Date(6)))
		var kanbanDataStates = readyStateConverter.convert(kanbanStates)
		kanbanDataStates.length should be (6)
		kanbanDataStates.count(x => x.state == "Dev" && x.date.getTime() == 1) should be (1)
		kanbanDataStates.count(x => x.state == "Dev Done" && x.date.getTime() == 2) should be (1)
		kanbanDataStates.count(x => x.state == "Test" && x.date.getTime() == 3) should be (1)
		kanbanDataStates.count(x => x.state == "Test Done" && x.date.getTime() == 4) should be (1)
		kanbanDataStates.count(x => x.state == "Test Done" && x.date.getTime() == 5) should be (1)
		kanbanDataStates.count(x => x.state == "Complete" && x.date.getTime() == 6) should be (1)
		print(kanbanDataStates)
	}

	"convert for multiple states with multiple ready states when out of order" should "change ready state to previous state with Done appended to end" in {
		val readyStateConverter = new ReadyStateConverter
		val kanbanStates = List(
		    new KanbanData("Complete", new Date(6)),
		    new KanbanData("Dev", new Date(1)), 
		    new KanbanData("Ready", new Date(5)),
		    new KanbanData("Test", new Date(3)), 
		    new KanbanData("Ready", new Date(2)), 
		    new KanbanData("Ready", new Date(4)))
		var kanbanDataStates = readyStateConverter.convert(kanbanStates)
		kanbanDataStates.length should be (6)
		kanbanDataStates.count(x => x.state == "Dev" && x.date.getTime() == 1) should be (1)
		kanbanDataStates.count(x => x.state == "Dev Done" && x.date.getTime() == 2) should be (1)
		kanbanDataStates.count(x => x.state == "Test" && x.date.getTime() == 3) should be (1)
		kanbanDataStates.count(x => x.state == "Test Done" && x.date.getTime() == 4) should be (1)
		kanbanDataStates.count(x => x.state == "Test Done" && x.date.getTime() == 5) should be (1)
		kanbanDataStates.count(x => x.state == "Complete" && x.date.getTime() == 6) should be (1)
		print(kanbanDataStates)
	}

	private def print(kanbanStates:List[KanbanData]) = {
	  for {
	    k <- kanbanStates
	  } yield println(k.state, k.date.getTime())
	}
}