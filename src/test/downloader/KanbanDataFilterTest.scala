package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import java.util.Date
import java.text.{SimpleDateFormat,DateFormat};

class KanbanDataFilterTest extends FlatSpec with ShouldMatchers{
	val kanbanFilter = new KanbanDataFilter
	
	"filter on a single item list" should "return the same value" in {
		val kanbanData = new KanbanData("Dev Started", new Date(1))
		var kanbanStates = kanbanFilter.filter(List(kanbanData))
		kanbanStates(0).state should be ("Dev Started")
		DateFormat.getDateInstance(DateFormat.SHORT).format(kanbanStates(0).date) should be ("1/1/70")
	}
	
	"filter on a multi item list" should "return the maximum date value" in {
		val kanbanData1 = new KanbanData("Dev Started", new Date(1))
		val kanbanData2 = new KanbanData("Dev Started", new Date(1000000000))
		var kanbanStates = kanbanFilter.filter(List(kanbanData1, kanbanData2, kanbanData2))
		kanbanStates.length should be (1)
		kanbanStates(0).state should be ("Dev Started")
		DateFormat.getDateInstance(DateFormat.SHORT).format(kanbanStates(0).date) should be ("1/13/70")
	}

	"filter on a multi item list with more than one activity state" should "return the maximum date value for each activity state" in {
		val kanbanData1 = new KanbanData("Dev Started", new Date(1))
		val kanbanData2 = new KanbanData("Dev Started", new Date(1000000000))
		val kanbanData3 = new KanbanData("Test Started", new Date(1))
		val kanbanData4 = new KanbanData("Test Started", new Date(1000000000))

		var kanbanStates = kanbanFilter.filter(List(kanbanData4, kanbanData2, kanbanData1, kanbanData4, kanbanData3))
		kanbanStates.length should be (2)
		kanbanStates.exists(x => x.state == "Dev Started" && DateFormat.getDateInstance(DateFormat.SHORT).format(x.date) == "1/13/70") should be (true)
		kanbanStates.exists(x => x.state == "Test Started" && DateFormat.getDateInstance(DateFormat.SHORT).format(x.date) == "1/13/70") should be (true)
	}	
}
