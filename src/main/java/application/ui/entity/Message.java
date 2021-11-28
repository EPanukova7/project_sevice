///*
// * Copyright 2012 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
// * the License. You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
// * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
// * specific language governing permissions and limitations under the License.
// */
//
//package application.ui.entity;
//
//import java.util.Calendar;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.validator.constraints.NotEmpty;
//
//import javax.persistence.*;
//
//@Entity
//public class Message {
//	// project - наша новая сущность
//
//	@Id
//	@Getter
//	@Setter
//	private Long id;
//
//	@Getter
//	@Setter
//	@NotEmpty(message = "Message is required.")
//	private String text;
//
//	@Getter
//	@Setter
//	@NotEmpty(message = "Summary is required.")
//	private String summary;
//
//	@Setter
//	@Getter
//	private Calendar created = Calendar.getInstance();
//
//
//}
