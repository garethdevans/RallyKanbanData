package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.xml._ 
import java.util.Date

class StoryPagerTest extends FlatSpec with ShouldMatchers {
  
  "Page count" should "equal the TotalResultCount divided by default PageSize when no remainder" in {
    var fakeRepository = new FakeRepository
    var fakeStoryBuilder = new FakeStoryBuilder
    fakeRepository.totalResultCount = 100
    var storyPager = new StoryPager(fakeRepository, fakeStoryBuilder)
    val project = new Project("name", "uri", List(), "username", "password")
    var stories = storyPager.page(project)
    stories.length should be (10)
    fakeStoryBuilder.buildCounter should be (5)
    fakeStoryBuilder.startIndex.length should be (5)
    fakeStoryBuilder.startIndex(4) should be (1)
    fakeStoryBuilder.startIndex(3) should be (21)
    fakeStoryBuilder.startIndex(2) should be (41)
    fakeStoryBuilder.startIndex(1) should be (61)
    fakeStoryBuilder.startIndex(0) should be (81)
  }

  "Page count" should "equal the TotalResultCount divided by default PageSize plus one when remainder not zero" in {
    var fakeRepository = new FakeRepository
    var fakeStoryBuilder = new FakeStoryBuilder
    fakeRepository.totalResultCount = 101
    var storyPager = new StoryPager(fakeRepository, fakeStoryBuilder)
    val project = new Project("name", "uri", List(), "username", "password")
    var stories = storyPager.page(project)
    stories.length should be (12)
    fakeStoryBuilder.buildCounter should be (6)
    fakeStoryBuilder.startIndex.length should be (6)
    fakeStoryBuilder.startIndex(5) should be (1)
    fakeStoryBuilder.startIndex(4) should be (21)
    fakeStoryBuilder.startIndex(3) should be (41)
    fakeStoryBuilder.startIndex(2) should be (61)
    fakeStoryBuilder.startIndex(1) should be (81)
    fakeStoryBuilder.startIndex(0) should be (101)
  }
  
  private class FakeStoryBuilder extends StoryBuilder{
    var buildCounter:Int = 0
    var startIndex:List[Int] = List()
    val story = new Story("id", "parentid", "name", "uri", "storyType", "Release", false, new Date(1), List())
    
    override def build(project:Project, page:Int) = { 
      buildCounter = buildCounter + 1
      startIndex = page :: startIndex
      List(story,story)
    } 
  }
  
  private class FakeRepository extends Repository{
    var totalResultCount = 0
    def getStoriesXml:Elem = { 
<QueryResult rallyAPIMajor="1" rallyAPIMinor="27">
   	<Errors/>
   	<Warnings/>
    <TotalResultCount>{totalResultCount}</TotalResultCount>
    <StartIndex>1</StartIndex>
    <PageSize>20</PageSize>
    <Results>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705056" refObjectName="Import Savings Activation as XML (taken off Kanban Board - not sure if/when will pull back)" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705082" refObjectName="Schema defined for Savings Activation trigger" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705107" refObjectName="Reformat the data extract from SAS so it has the fields required to import into m-savvy." type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705133" refObjectName="[BVI62]: Find mapping tool to map Savings Activation data to the m-savvy schema" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705159" refObjectName="[BVI62]: Create data for Savings Activation as XML file" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705185" refObjectName="M-savvy work to automatically expose schema" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705211" refObjectName="[BVI62]: Development work in m-savvy to receive and process XML file" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705237" refObjectName="Transfer a Valid XML file to m-savvy from CLM team" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705263" refObjectName="Transfer 2 Invalid XML files to m-savvy from CLM team" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705289" refObjectName="Develop email notification for success failure of XML Data load" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705833" refObjectName="Post implementation verification for putting m-savvy into Production" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705859" refObjectName="Change the mailhouse letter formats to match the data coming from m-savvy for Anniversary Savings Activation" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705885" refObjectName="Create training documentation for Production process in m-savvy" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705910" refObjectName="Run (Sun 10th) and verify (Mon 11th) the Savings Activation trigger automatically and manually in Production" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705936" refObjectName="Update remaining configuration in m-savvy by the CLM business team" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705962" refObjectName="Verify successful end to end delivery of the Anniversary and Savings Activation triggers on 12 July" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3784705988" refObjectName="Verify successful end to end delivery of the AP Topup trigger" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3836041623" refObjectName="Add profile data for all Main Bank Active customers into m-savvy" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3836041648" refObjectName="Do a check of the SFTP process with the file in Test (import subset into m-savvy)" type="HierarchicalRequirement"/>
    	<Object rallyAPIMajor="1" rallyAPIMinor="27" ref="https://rally1.rallydev.com/slm/webservice/1.27/hierarchicalrequirement/3836041674" refObjectName="AFTER US125 DONE: Import Profile data for Main Bank Active customers into m-savvy in Production" type="HierarchicalRequirement"/>
    </Results>
</QueryResult>
    }
          
    override def getXml(project:Project, resource:String) = getStoriesXml
  }
}