package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import java.util.Date

class BlankDateFillerTest extends FlatSpec with ShouldMatchers {

	"Fill when the second and third dates are null for the same state" should "put the last date into the second and third date" in {		
		val states = List(new KanbanData("design", new Date(1)), 
		    new KanbanData("dev", null), 
		    new KanbanData("dev", null), 
		    new KanbanData("test", new Date(10000000)))
		
		val statesFilled = new BlankDateFiller().fill(List("design", "dev", "test"), states)
		
		for{
		  s <- statesFilled
		} yield println(s.date, s.state)
		
		statesFilled.length should equal (4)
		statesFilled(0).state should equal ("design")
		statesFilled(0).date.getTime() should equal (1)
		statesFilled(1).state should equal ("dev")
		statesFilled(1).date.getTime() should equal (10000000)
		statesFilled(2).state should equal ("dev")
		statesFilled(2).date.getTime() should equal (10000000)
		statesFilled(3).state should equal ("test")
		statesFilled(3).date.getTime() should equal (10000000)
	}

	"Fill when the last date is null" should "leave the last date null" in {		
		val states = List(new KanbanData("design", new Date(1)), 
		    new KanbanData("dev", new Date(10000000)), 
		    new KanbanData("test", null))
		
		val statesFilled = new BlankDateFiller().fill(List("design", "dev", "test"), states)
				
		statesFilled.length should equal (3)
		statesFilled(0).state should equal ("design")
		statesFilled(0).date.getTime() should equal (1)
		statesFilled(1).state should equal ("dev")
		statesFilled(1).date.getTime() should equal (10000000)
		statesFilled(2).state should equal ("test")
		statesFilled(2).date should equal (null)
	}

	"Fill when two dates including last is null and an extra state exists" should "leave the last two dates null and add extra ready and ready done states" in {		
		val states = List(new KanbanData("design", new Date(1)), 
		    new KanbanData("dev", null), 
		    new KanbanData("test", new Date(10000000)), 
		    new KanbanData("release ready", null),
		    new KanbanData("release", null))
		
		val statesFilled = new BlankDateFiller().fill(List("ready", "ready done", "design", "dev", "test", "release ready", "release"), states)
				
		statesFilled.length should equal (7)
		statesFilled(0).state should equal ("ready")
		statesFilled(0).date.getTime() should equal (1)
		statesFilled(1).state should equal ("ready done")
		statesFilled(1).date.getTime() should equal (1)
		statesFilled(2).state should equal ("design")
		statesFilled(2).date.getTime() should equal (1)
		statesFilled(3).state should equal ("dev")
		statesFilled(3).date.getTime() should equal (10000000)
		statesFilled(4).state should equal ("test")
		statesFilled(4).date.getTime() should equal (10000000)
		statesFilled(5).state should equal ("release ready")
		statesFilled(5).date should equal (null)
		statesFilled(6).state should equal ("release")
		statesFilled(6).date should equal (null)
	}

}