package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.xml._ 
import java.util.Date

class KanbanDataBuilderTest extends FlatSpec with ShouldMatchers{

	"build" should "call filter once" in {
		var fakeKanbanStateMatcher = new FakeKanbanStateMatcher
		var kanbanDataBuilder = new KanbanDataBuilder(new FakeRepository, fakeKanbanStateMatcher, new FakeRevisionParser, new FakeDateParser)
		var kanbanStates = kanbanDataBuilder.build(new Project("name", "uri", List(), "username", "password"), "revisionHistoryUri")
		fakeKanbanStateMatcher.matchCounter should equal (1)
	}

	"build" should "add only Descriptions containing KANBANSTATE, SCHEDULE STATE and READY changes" in {
		var fakeKanbanStateMatcher = new FakeKanbanStateMatcher
		var fakeRevisionParser = new FakeRevisionParser
		var kanbanDataBuilder = new KanbanDataBuilder(new FakeRepository, fakeKanbanStateMatcher, fakeRevisionParser, new FakeDateParser)
		var kanbanStates = kanbanDataBuilder.build(new Project("name", "uri", List(), "username", "password"), "revisionHistoryUri")		
		kanbanStates.length should be (4)
		kanbanStates.count(k => k.state == "KANBANSTATE changed from [Dev (Stories)] to [Release Ready]" && k.date.getTime() == 12345 ) should be (1)
		kanbanStates.count(k => k.state == "NAME changed from [Bill] to [Bob], KANBANSTATE changed from [Dev (Stories)] to [Test (Stories)]" && k.date.getTime() == 12345 ) should be (1)
		kanbanStates.count(k => k.state == "READY changed from [false] to [true]" && k.date.getTime() == 12345 ) should be (1)
		kanbanStates.count(k => k.state == "SCHEDULE STATE changed from [In-Progress] to [Completed], TASK REMAINING TOTAL changed from [1.0] to [0.0]" && k.date.getTime() == 12345 ) should be (1)
		fakeRevisionParser.parseCounter should be (4)
	}

	private class FakeKanbanStateMatcher extends KanbanStateMatcher{
		var matchCounter = 0
		override def matchStates(nameStates:List[String], kanbanStates:List[KanbanData]):List[KanbanData] = {matchCounter = matchCounter + 1; kanbanStates}
	}

	private class FakeRevisionParser extends RevisionParser{
		var parseCounter = 0
		override def parse(revision:String):String = { parseCounter = parseCounter + 1; revision}
	}
		
	private class FakeDateParser extends DateParser{
		override def parse(creationDate:String):Date = { new Date(12345) }
	}

  	private class FakeRepository extends Repository{
		def getRevisionHistoryXml:Elem = { 
<RevisionHistory rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/revisionhistory/3784705057" objectVersion="1" CreatedAt="Jul 6">
  		<CreationDate>2011-07-06T05:04:20.146Z</CreationDate>
  		<ObjectID>3784705057</ObjectID>
  		<Subscription rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/subscription/2712985125" refObjectName="BNZ" type="Subscription"/>
  		<Workspace rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/workspace/3684486830" refObjectName="Customer Experience" type="Workspace"/>
  		<Revisions>
  			<Revision rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/revision/4342105532" objectVersion="1" CreatedAt="Sep 19" type="Revision">
  				<CreationDate>2011-09-22T23:51:42.198Z</CreationDate>
  				<ObjectID>4342105532</ObjectID>
  				<Subscription rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/subscription/2712985125" refObjectName="BNZ" type="Subscription"/>
  				<Workspace rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/workspace/3684486830" refObjectName="Customer Experience" type="Workspace"/>
  				<Description>KANBANSTATE changed from [Dev (Stories)] to [Release Ready]</Description>
  				<RevisionNumber>56</RevisionNumber>
  				<User rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/user/3684508294" refObjectName="David A King" type="User"/>
  			</Revision>
  			<Revision rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/revision/4342105532" objectVersion="1" CreatedAt="Sep 19" type="Revision">
  				<CreationDate>2011-09-18T23:51:42.198Z</CreationDate>
  				<ObjectID>4342105532</ObjectID>
  				<Subscription rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/subscription/2712985125" refObjectName="BNZ" type="Subscription"/>
  				<Workspace rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/workspace/3684486830" refObjectName="Customer Experience" type="Workspace"/>
  				<Description>NAME changed from [Bill] to [Bob], KANBANSTATE changed from [Dev (Stories)] to [Test (Stories)]</Description>
  				<RevisionNumber>56</RevisionNumber>
  				<User rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/user/3684508294" refObjectName="David A King" type="User"/>
  			</Revision>
  			<Revision rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/revision/4342105491" objectVersion="1" CreatedAt="Sep 19" type="Revision">
  				<CreationDate>2011-09-18T23:51:35.724Z</CreationDate>
  				<ObjectID>4342105491</ObjectID>
  				<Subscription rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/subscription/2712985125" refObjectName="BNZ" type="Subscription"/>
  				<Workspace rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/workspace/3684486830" refObjectName="Customer Experience" type="Workspace"/>
  				<Description>READY changed from [false] to [true]</Description>
  				<RevisionNumber>55</RevisionNumber>
  				<User rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/user/3684508294" refObjectName="David A King" type="User"/>
  			</Revision>
  			<Revision rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/revision/4342105491" objectVersion="1" CreatedAt="Sep 19" type="Revision">
  				<CreationDate>2011-09-18T23:51:35.724Z</CreationDate>
  				<ObjectID>4342105491</ObjectID>
  				<Subscription rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/subscription/2712985125" refObjectName="BNZ" type="Subscription"/>
  				<Workspace rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/workspace/3684486830" refObjectName="Customer Experience" type="Workspace"/>
  				<Description>SCHEDULE STATE changed from [In-Progress] to [Completed], TASK REMAINING TOTAL changed from [1.0] to [0.0]</Description>
  				<RevisionNumber>55</RevisionNumber>
  				<User rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/user/3684508294" refObjectName="David A King" type="User"/>
  			</Revision>
  			<Revision rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/revision/4342105491" objectVersion="1" CreatedAt="Sep 19" type="Revision">
  				<CreationDate>2011-09-15T23:51:35.724Z</CreationDate>
  				<ObjectID>4342105491</ObjectID>
  				<Subscription rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/subscription/2712985125" refObjectName="BNZ" type="Subscription"/>
  				<Workspace rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/workspace/3684486830" refObjectName="Customer Experience" type="Workspace"/>
  				<Description>READY changed from [true] to [false], NAME changed from [Import Savings Activation as XML] to [Import Savings Activation]</Description>
  				<RevisionNumber>55</RevisionNumber>
  				<User rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/user/3684508294" refObjectName="David A King" type="User"/>
  			</Revision>
  		</Revisions>
</RevisionHistory>
    }

    override def getXml(project:Project, resource:String) = getRevisionHistoryXml
  }

}