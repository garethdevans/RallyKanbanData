package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class RevisionParserTest extends FlatSpec with ShouldMatchers {
	val revisionParser = new RevisionParser

	"A single Schedule state change" should "return the Activity state only" in {
		revisionParser.parse("SCHEDULE STATE changed from [In-Progress] to [Completed]") should equal("Completed")
	}

	"Multiple changes with a Schedule state change" should "return the Activity state only" in {
		revisionParser.parse("SCHEDULE STATE changed from [In-Progress] to [Completed], TASK REMAINING TOTAL changed from [1.0] to [0.0]") should equal("Completed")
	}
	
	"A single Kanban state added" should "return the Activity state only" in {
		revisionParser.parse("KANBANSTATE Added [Dev (Stories)]") should equal("Dev (Stories)")
	}

	"Multiple changes with Kanban state added" should "return the Activity state only" in {
		revisionParser.parse("A Change to [This State],KANBANSTATE Added [Dev (Stories)] , Some other change [State]") should equal("Dev (Stories)")
	}

	"A single Kanban state changed" should "return the Activity state only" in {
		revisionParser.parse("KANBANSTATE Changed from [Dev (Stories)] to [Test (Stories)]") should equal("Test (Stories)")
	}

	"Multiple changes with Kanban state changed" should "return the Activity state only" in {
		revisionParser.parse("A Change to [This State], KANBANSTATE Changed from [Dev (Stories)] to [Test (Stories)],Some other change [State]") should equal("Test (Stories)")
	}
	
	"Ready state change to true" should "return ready state" in {
		revisionParser.parse("A Change to [This State],Ready changed from [false] to [true], Name changed to [Fred]") should equal("Ready")
	}
	
	"Task state change from 0.0" should "return Defined state" in {
		revisionParser.parse("A Change to [This State], TASK ESTIMATE TOTAL changed from [0.0], Name changed to [Fred]") should equal("Defined")
	}	

	"Multiple changes with Devo Kanban state added and many spaces between states" should "return the Activity state only" in {
		revisionParser.parse("Some other change [State]  ,   A Change to [This State]   ,  DevoKANBANSTATE Added [Dev (Stories)]") should equal("Dev (Stories)")
	}
	
}
