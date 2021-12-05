/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package application.ui;

import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.repository.ProjectRepository;
import application.ui.repository.TaskRepository;
//import application.ui.service.ProjectService;
//import application.ui.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SampleWebUiApplication {
//	@Autowired
//	private ProjectRepository projectRepository;
//	@Autowired
//	private TaskRepository taskRepository;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleWebUiApplication.class, args);
	}

//	@Bean
//	public ProjectRepository projectRepository() {
//		return new ProjectService();
//	}

//	@Bean
//	public TaskRepository taskRepository() {
//		return new TaskService();
//	}

//	@Bean
//	public Converter<String, Project> projectConverter() {
//		return new Converter<String, Project>() {
//			@Override
//			public Project convert(String id) {
//				return projectRepository().findProject(Integer.valueOf(id));
//			}
//		};
//	}
//
//	@Bean
//	public Converter<String, Task> taskConverter() {
//		return new Converter<String, Task>() {
//			@Override
//			public Task convert(String id) {
//				return taskRepository().findTask(Integer.valueOf(id));
//			}
//		};
//	}

}
