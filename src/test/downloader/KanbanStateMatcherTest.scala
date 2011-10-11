package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import java.util.Date

class KanbanStateMatcherTest extends FlatSpec with ShouldMatchers{
	"matcher with no states" should "return a list matching the project kanban states with null dates" in {
		val kanbanStateMatcher = new KanbanStateMatcher
		val project = new Project("", "", List("Dev", "Test"), "", "")
		val kanbanStates = kanbanStateMatcher.matchStates(project, List())
		kanbanStates.length should be (2)
		kanbanStates.count(x=>x.state == "Test" && x.date == null) should be (1)		
		kanbanStates.count(x=>x.state == "Dev" && x.date == null) should be (1)
	}

	"matcher with one state and different case with spaces" should "return the matching state and unknown states wih null dates" in {
		val kanbanStateMatcher = new KanbanStateMatcher
		val project = new Project("", "", List("DEV", "TEST ", "RELEASED"), "", "")
		val kanbanStates = kanbanStateMatcher.matchStates(project, List(new KanbanData(" Test", new Date(1))))
		kanbanStates.length should be (3)
		kanbanStates.count(x=>x.state == " Test" && x.date.getTime() == 1) should be (1)
		kanbanStates.count(x=>x.state == "DEV" && x.date == null) should be (1)
		kanbanStates.count(x=>x.state == "RELEASED" && x.date == null) should be (1)
	}
	
	"matcher with two states with different dates" should "return the matching state with maximum date" in {
		val kanbanStateMatcher = new KanbanStateMatcher
		val project = new Project("", "", List("Dev", "Test"), "", "")
		val kanbanStates = kanbanStateMatcher.matchStates(project, List(new KanbanData("Test", new Date(1)), new KanbanData("Test", new Date(2))))
		kanbanStates.length should be (2)
		kanbanStates.count(x=>x.state == "Test" && x.date.getTime() == 2) should be (1)
		kanbanStates.count(x=>x.state == "Dev" && x.date == null) should be (1)
	}

	"matcher with two different states with two different dates and missing states" should "return the matching states with maximum date for each" in {
		val kanbanStateMatcher = new KanbanStateMatcher
		val project = new Project("", "", List("Dev", "Test"), "", "")
		val kanbanStates = kanbanStateMatcher.matchStates(project, List(
		    new KanbanData("Test", new Date(4)),
		    new KanbanData("Dev", new Date(1)),
		    new KanbanData("Missing State", new Date(1)),
		    new KanbanData("Test", new Date(3)),
		    new KanbanData("Dev", new Date(2))))
		kanbanStates.length should be (2)
		kanbanStates.count(x=>x.state == "Test" && x.date.getTime() == 4) should be (1)
		kanbanStates.count(x=>x.state == "Dev" && x.date.getTime() == 2) should be (1)
	}

}