package downloader

import scala.collection.mutable.ListBuffer

class StoryPager(
    val repository:Repository,
    val storyBuilder:StoryBuilder){
	private val configManager = new ConfigManager 
	
	def this() = this(new Repository, new StoryBuilder) 
	
	def page(project:Project):List[Story] = {
		var stories = new ListBuffer[Story]
		for (i <- 1 until pageCount(project)+1)
			stories.appendAll(storyBuilder.build(project, i))
		stories.toList
	}
	
	private def pageCount(project:Project):Int = {
		val total = (repository.getXml(project, configManager.storiesUri)\"TotalResultCount").text.toInt
		if (total % 20 == 0) total/20 else (total/20) + 1
	}
}
