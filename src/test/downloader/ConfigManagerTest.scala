package downloader

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
  
class ConfigManagerTest extends FlatSpec with ShouldMatchers {
	val configManager = new ConfigManager("../configTest.xml")

	"ConfigManager" should "only find one project" in {
		configManager.projects.length should be (1)
	}
	
	"Single ConfigManager project" should "have name, username, password and uri" in {
		val project = configManager.projects.head
		project.name should be ("CRM")
		project.username should be ("Bob")
		project.password should be ("pa55word")
		project.uri should be ("https://rally1.rallydev.com/slm/webservice/1.27/project/3684505712")
	}

	"KanbanSates for single project" should "have all states and correct count" in {
		val project = configManager.projects.head
		project.kanbanStates.length should be (2)
		project.kanbanStates(0) should be ("Dev Started")
		project.kanbanStates(1) should be ("Dev Done")
	}
}