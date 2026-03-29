<script setup>
import { ref, onMounted } from 'vue'

const posts = ref([])
const loading = ref(true)

const fetchPosts = async () => {
  try {
    const response = await fetch((import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080') + '/api/gallery')
    if (response.ok) {
      posts.value = await response.json()
    }
  } catch (error) {
    console.error('Error fetching gallery:', error)
  } finally {
    loading.value = false
  }
}

onMounted(fetchPosts)
</script>

<template>
  <section class="social-wall">
    <div class="container">
      <div class="section-header">
        <span class="badge">Nossa Tribo</span>
        <h2>Compartilhando o Mate e a Paixão</h2>
        <p>Veja como nossos clientes personalizam suas térmicas com a Calco.Mania.</p>
      </div>

      <div v-if="loading" class="loading">Inspirando a tribo...</div>

      <div v-else-if="posts.length === 0" class="gallery-placeholder">
        <div class="placeholder-icon">📷</div>
        <p>Ainda não temos fotos na galeria. Seja o primeiro a compartilhar!</p>
      </div>

      <div v-else class="masonry-grid">
        <div v-for="post in posts" :key="post.id" class="post-card">
          <div class="post-image">
            <img :src="post.imageUrl" :alt="post.caption" loading="lazy" />
          </div>
          <div class="post-overlay">
            <span class="username">@{{ post.username }}</span>
            <p v-if="post.caption" class="caption">{{ post.caption }}</p>
          </div>
        </div>
      </div>

      <div class="social-footer">
        <p>Quer aparecer aqui? Use a tag <strong>#CalcoMania</strong> no Instagram!</p>
        <button class="btn-secondary">Enviar Minha Foto</button>
      </div>
    </div>
  </section>
</template>

<style scoped>
.social-wall {
  padding: 100px 0;
  background-color: var(--color-white);
}

.section-header {
  text-align: center;
  margin-bottom: 60px;
}

.badge {
  color: var(--color-leaf);
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  font-size: 0.825rem;
  margin-bottom: 12px;
  display: block;
}

.section-header h2 {
  font-size: 2.5rem;
  margin-bottom: 16px;
}

.loading {
  text-align: center;
  padding: 60px;
  font-style: italic;
  color: var(--color-forest-light);
}

.masonry-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  grid-auto-rows: 320px;
  gap: 20px;
}

.post-card {
  position: relative;
  background: var(--color-cream-dark);
  border-radius: var(--radius-md);
  overflow: hidden;
  transition: transform var(--transition-normal);
}

.post-card:hover {
  transform: scale(1.02);
}

.post-image {
  height: 100%;
  width: 100%;
}

.post-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.post-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  padding: 24px;
  background: linear-gradient(to top, rgba(8, 28, 21, 0.8), transparent);
  color: var(--color-white);
  opacity: 0;
  transition: opacity 0.3s;
}

.post-card:hover .post-overlay {
  opacity: 1;
}

.username {
  font-weight: 700;
  font-size: 1rem;
  margin-bottom: 4px;
  display: block;
}

.caption {
  font-size: 0.875rem;
  line-height: 1.4;
  opacity: 0.9;
}

.gallery-placeholder {
  text-align: center;
  padding: 80px;
  border: 2px dashed var(--color-cream-dark);
  border-radius: var(--radius-lg);
  color: var(--color-forest-soft);
}

.placeholder-icon {
  font-size: 3rem;
  margin-bottom: 16px;
}

.social-footer {
  text-align: center;
  margin-top: 60px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;
}

.social-footer p {
  color: var(--color-forest-base);
}
</style>
