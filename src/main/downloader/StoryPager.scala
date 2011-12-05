package downloader

import scala.collection.mutable.ListBuffer

class StoryPager(
    val repository:Repository,
    val storyBuilder:StoryBuilder){
	private val pageSize:Int = 20
	private val configManager = new ConfigManager 
	
	def this() = this(new Repository, new StoryBuilder) 
	
	def page(project:Project):List[Story] = {
		var stories = new ListBuffer[Story]
		for (i <- 0 until pageCount(project))
			stories.appendAll(storyBuilder.build(project, (i*pageSize)+1))
		stories.toList
	}
	
	private def pageCount(project:Project):Int = {
		val total = (repository.getXml(project, configManager.storiesUri)\"TotalResultCount").text.toInt
		if (total % pageSize == 0) total/pageSize else (total/pageSize) + 1
	}
}
