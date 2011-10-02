package downloader

class ProjectBuilder(){
    private val storyPager = new StoryPager
	private val configManager = new ConfigManager
	
	def buildAll: List[Project] = {
		var projects = configManager.projects
		projects foreach {(project) =>
			project.stories = storyPager.page(project)
		}
		return projects	
	}
}
