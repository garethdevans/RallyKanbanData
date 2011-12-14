package downloader

class ProjectBuilder(){
    private val storyPager = new StoryPager
	private val configManager = new ConfigManager
	
	def output = {
		configManager.projects foreach {(project) =>
			project.stories = storyPager.page(project)
			project.toCsv
		}
	}
    
}
