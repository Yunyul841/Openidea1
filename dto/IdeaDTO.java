package dto;

public class IdeaDTO {
	
		// 컬럼명과 변수명을 일치시켜서 편의성을 높인다
		private int num;
		private String title;
		private String content;
		private String writer;
		private String indate;

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getWriter() {
			return writer;
		}

		public void setWriter(String writer) {
			this.writer = writer;
		}

		public String getIndate() {
			return indate;
		}

		public void setIndate(String indate) {
			this.indate = indate;
		}

		// 디버깅용 toString
		@Override
		public String toString() {
			return "IdeaDTO [num=" + num + ", title=" + title + ", content=" + content + ", writer=" + writer + ", indate="
					+ indate + "]";
		}

	}


