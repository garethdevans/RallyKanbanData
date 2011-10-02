package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class RevisionParserTest extends FlatSpec with ShouldMatchers {
	val revisionParser = new RevisionParser

	"A single Kanban state change with added" should "return the Activity state" in {
		revisionParser.parse("KANBANSTATE Added [Dev (Stories)]") should equal("Dev (Stories)")
	}

	"Multiple Kanban state changes with added" should "return the Activity state" in {
		revisionParser.parse("A Change to [This State],KANBANSTATE Added [Dev (Stories)], Some other change [State]") should equal("Dev (Stories)")
	}

	"A single Kanban state change with changed" should "return the Activity state" in {
		revisionParser.parse("KANBANSTATE Changed from [Dev (Stories)] to [Test (Stories)]") should equal("Test (Stories)")
	}

	"Multiple Kanban state changes with changed" should "return the Activity state" in {
		revisionParser.parse("A Change to [This State], KANBANSTATE Changed from [Dev (Stories)] to [Test (Stories)],Some other change [State]") should equal("Test (Stories)")
	}
	
	"Ready state change to true" should "return ready state" in {
		revisionParser.parse("A Change to [This State],Ready changed from false to true") should equal("Done")
	}	
}
