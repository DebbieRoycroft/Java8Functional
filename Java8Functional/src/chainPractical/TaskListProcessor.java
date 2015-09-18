package chainPractical;

import java.util.List;


public class TaskListProcessor {
	private TaskListFilterer filterChain;
	private TaskListSorter sorter;
	
	public TaskListProcessor(TaskListFilterer filterChain, 
			TaskListSorter sorter){
		this.filterChain = filterChain;
		this.sorter = sorter;
	}
	
	public List<Task> filterAndSortList(List<Task> tasks){
		List<Task> filteredList = filterChain.filter(tasks);
		return sorter.sort(filteredList);
	}
}
