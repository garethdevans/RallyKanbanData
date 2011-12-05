package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.xml._ 
import java.util.Date

class StoryBuilderTest extends FlatSpec with ShouldMatchers {
	
	"build with no children" should "return no stories" in {
	  var fakeRepository = new FakeRepository
	  fakeRepository.children = fakeRepository.hasChildren 
	  var fakeKanbanDataBuilder = new FakeKanbanDataBuilder
	  var storyBuilder = new StoryBuilder(fakeRepository, fakeKanbanDataBuilder, new DateParser)
	  var stories = storyBuilder.build(new Project("name", "uri", List(), "username", "password"), 1)
	  stories.length should be (3)
	  stories.count(s => s.id == "US62" 
	    && s.parentId == "US12" 
	    && s.name == "Import; Savings" 
	    && s.storyType == "BVI" 
	    && s.release == "Release; 3" 
	    && s.hasChildren
	    && s.creationDate.getTime() == 1317740341929L) should be (3)
	  fakeKanbanDataBuilder.buildCounter should be (3)
	}

	"build with children" should "return a story and call kanbanDataBuilder for each story in page of 3" in {
	  var fakeRepository = new FakeRepository
	  var fakeKanbanDataBuilder = new FakeKanbanDataBuilder
	  var storyBuilder = new StoryBuilder(fakeRepository, fakeKanbanDataBuilder, new DateParser)
	  var stories = storyBuilder.build(new Project("name", "uri", List(), "username", "password"), 1)
	  stories.length should be (3)
	  stories.count(s => s.id == "US62" 
	    && s.parentId == "US12"
	    && s.name == "Import; Savings" 
	    && s.storyType == "BVI" 
	    && s.release == "Release; 3" 
	    && !s.hasChildren
	    && s.creationDate.getTime() == 1317740341929L) should be (3)	  
	  stories.count(s => s.uri == "https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705056") should be (1)
	  stories.count(s => s.uri == "https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705082") should be (1)
	  stories.count(s => s.uri == "https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3836041674") should be (1)
	  fakeKanbanDataBuilder.buildCounter should be (3)
	}

	private class FakeKanbanDataBuilder extends KanbanDataBuilder{
		var buildCounter:Int = 0
		override def build(project:Project, revisionHistoryUri:String) = {buildCounter = buildCounter +1; List()}
	}
	
	private class FakeRepository extends Repository{
	  var children:Elem = <Children/>
	  val hasChildren:Elem =
<Children>
<HierarchicalRequirement rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/4400264225" refObjectName="Channel reporting - placeholder" type="HierarchicalRequirement"/>
<HierarchicalRequirement rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/4400207538" refObjectName="GDW" type="HierarchicalRequirement"/>
<HierarchicalRequirement rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/4400263132" refObjectName="CSU" type="HierarchicalRequirement"/>
</Children>
	    
		def getStoriesXml:Elem = { 
<QueryResult rallyAPIMajor="1" rallyAPIMinor="27">
   	<Errors/>
   	<Warnings/>
    <TotalResultCount>103</TotalResultCount>
    <StartIndex>6</StartIndex>
    <PageSize>20</PageSize>
    <Results>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705056" refObjectName="Import Savings Activation as XML (taken off Kanban Board - not sure if/when will pull back)" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705082" refObjectName="Schema defined for Savings Activation trigger" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3836041674" refObjectName="AFTER US125 DONE: Import Profile data for Main Bank Active customers into m-savvy in Production" type="HierarchicalRequirement"/>
    </Results>
</QueryResult>
    }

	def getStoryXml:Elem = {
<HierarchicalRequirement rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705056" objectVersion="61" refObjectName="Import Savings Activation as XML (taken off Kanban Board - not sure if/when will pull back)" CreatedAt="Jul 6">
    <FormattedID>US62</FormattedID>
    <Name>Import, Savings</Name>
    <Project rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/project/3715993696" refObjectName="CLM" type="Project"/>
    <RevisionHistory rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/revisionhistory/3784705057" type="RevisionHistory"/>
    <StoryType>BVI</StoryType>
    {children}
    <Release rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/release/4426816541" refObjectName="Release, 3" type="Release"/>
    <CreationDate>2011-10-05T03:59:01.929Z</CreationDate>
    <Parent rallyAPIMajor="1" rallyAPIMinor="27" ref="https://parent" refObjectName="Post implementation" type="HierarchicalRequirement"/>
</HierarchicalRequirement>	  
	}

	def getParentStoryXml:Elem = {
<HierarchicalRequirement rallyAPIMajor="1" rallyAPIMinor="27" ref="https://parent" >
    <FormattedID>US12</FormattedID>
</HierarchicalRequirement>	  
	}

    override def getXml(project:Project, resource:String, page:Int) = getStoriesXml
    override def getXml(project:Project, resource:String) = { if (resource=="https://parent") getParentStoryXml else getStoryXml}
  }
	
}