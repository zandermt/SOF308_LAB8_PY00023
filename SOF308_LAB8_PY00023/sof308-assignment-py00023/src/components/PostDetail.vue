<template>
  <br />
  <div class="post-detail">
    <div v-if="loading">Đang tải chi tiết bài viết...</div>
    <div v-else>
      <h1>{{ article.title }}</h1>
      <p><strong>Ngày đăng:</strong> {{ formatDate(article.postedDate) }}</p>

      <div v-html="article.content"></div>

      <div class="comment" v-for="comment in comments" :key="comment.id">
        <div style="display: flex; align-items: center; margin-bottom: 10px">
          <img
            src="https://i.pravatar.cc/40"
            alt="Avatar"
            style="
              width: 40px;
              height: 40px;
              border-radius: 50%;
              margin-right: 10px;
            "
          />
          <p style="margin: 0; font-size: 1rem; font-weight: 600; color: #333">
            {{ comment.author }}
          </p>
        </div>
        <p style="margin-left: 50px">{{ comment.content }}</p>
      </div>
    </div>
  </div>
  <br />
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      article: null,
      loading: true,
    };
  },
  props: ["id"],
  mounted() {
    this.fetchArticle();
  },
  methods: {
    async fetchArticle() {
      try {
        const response = await axios.get(
          `http://localhost:8080/VueJS_Backend/api/articles/${this.id}`
        );
        this.article = response.data;
      } catch (error) {
        console.error("Lỗi khi tải bài viết:", error);
      } finally {
        this.loading = false;
      }
    },
    formatDate(dateString) {
      const options = { year: "numeric", month: "long", day: "numeric" };
      return new Date(dateString).toLocaleDateString("vi-VN", options);
    },
  },
};
</script>

<style>
.post-detail {
  max-width: 1200px;
  margin: 20px auto;
  padding: 25px;
  font-family: "Poppins", sans-serif;
  background: linear-gradient(145deg, #ffffff, #f0f0f0);
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
}

.post-detail h1 {
  font-size: 2.8rem;
  font-weight: 700;
  color: #222;
  margin-bottom: 15px;
  text-align: center;
}

.post-detail p {
  font-size: 1.1rem;
  color: #555;
  margin-bottom: 20px;
}

.post-detail p strong {
  font-weight: 600;
  color: #000;
}

.comment-section {
  margin-top: 50px;
  padding-top: 25px;
  border-top: 2px solid #e5e5e5;
}

.comment-section h2 {
  font-size: 2rem;
  font-weight: 600;
  margin-bottom: 25px;
  color: #333;
  text-align: center;
}

.comment-section .comment {
  margin-bottom: 20px;
  padding: 20px;
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s ease-in-out;
}

.comment-section .comment:hover {
  transform: translateY(-5px);
}

.comment-section .comment p {
  margin: 0;
  font-size: 1rem;
  color: #444;
  line-height: 1.5;
}

.comment-section .comment p strong {
  color: #007bff;
}

.comment-section .add-comment {
  margin-top: 30px;
}

.comment-section textarea {
  width: 100%;
  min-height: 120px;
  padding: 15px;
  border: 2px solid #ddd;
  border-radius: 10px;
  font-size: 1rem;
  resize: none;
  font-family: "Poppins", sans-serif;
  background: #f8f8f8;
}

.comment-section textarea:focus {
  border-color: #007bff;
  outline: none;
  background: #fff;
  box-shadow: 0 0 10px rgba(0, 123, 255, 0.2);
}

.comment-section button {
  margin-top: 15px;
  padding: 12px 30px;
  font-size: 1rem;
  font-weight: 600;
  background: linear-gradient(145deg, #007bff, #0056b3);
  color: #fff;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.comment-section button:hover {
  background: #0056b3;
  box-shadow: 0 5px 15px rgba(0, 91, 187, 0.3);
}
</style>
