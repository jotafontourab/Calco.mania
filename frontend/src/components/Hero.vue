<script setup>
</script>

<template>
  <section class="hero-section">
    <div class="container hero-content">
      <div class="hero-text">
        <h1>Sua térmica conta uma história.</h1>
        <p>Tem coisas que não se explicam. Se colam.</p>
        <div class="hero-actions">
        </div>
      </div>
      <div class="hero-image">
        <div class="placeholder-termica">
          <img src="/images/mate-drawing.png" alt="Desenho de Mate" class="mate-drawing" />
          <span>Sua parceira de mate</span>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.hero-section {
  padding: 80px 0;
  background: linear-gradient(135deg, var(--color-forest-base) 0%, var(--color-forest-dark) 100%);
  color: var(--color-cream);
}
.hero-content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 48px;
  align-items: center;
}
.hero-text h1 {
  font-size: 3.5rem;
  color: var(--color-cream);
  margin-bottom: 24px;
}
.hero-text p {
  font-size: 1.25rem;
  color: var(--color-leaf);
  margin-bottom: 32px;
  max-width: 500px;
}
.hero-actions {
  display: flex;
  gap: 16px;
}
.hero-image {
  display: flex;
  justify-content: center;
}
.placeholder-termica {
  width: 320px;
  height: 480px;
  background-color: rgba(255,255,255,0.05);
  border: 2px dashed var(--color-leaf);
  border-radius: var(--radius-lg);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 16px;
  color: var(--color-leaf);
  font-family: var(--font-serif);
  font-style: italic;
  font-size: 1.2rem;
  backdrop-filter: blur(10px);
}
.mate-drawing {
  max-width: 80%;
  max-height: 70%;
  object-fit: contain;
  filter: drop-shadow(0 10px 20px rgba(0,0,0,0.2));
}
@media (max-width: 768px) {
  .hero-content {
    grid-template-columns: 1fr;
    text-align: center;
  }
  .hero-text p {
    margin: 0 auto 32px auto;
  }
  .hero-actions {
    justify-content: center;
  }
}
</style>
