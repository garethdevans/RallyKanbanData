package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class RevisionParserTest extends FlatSpec with ShouldMatchers {
	val revisionParser = new RevisionParser

	"A single Kanban state added" should "return the Activity state only" in {
		revisionParser.parse("KANBANSTATE Added [Dev (Stories)]") should equal("Dev (Stories)")
	}

	"Multiple changes with Kanban state added" should "return the Activity state only" in {
		revisionParser.parse("A Change to [This State],KANBANSTATE Added [Dev (Stories)], Some other change [State]") should equal("Dev (Stories)")
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
}
