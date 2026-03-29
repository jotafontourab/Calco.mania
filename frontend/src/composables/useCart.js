import { reactive, computed } from 'vue'

const state = reactive({
  items: []
})

export function useCart() {
  const addToCart = (product) => {
    const existing = state.items.find(i => i.id === product.id)
    if (existing) {
      existing.quantity++
    } else {
      state.items.push({ ...product, quantity: 1 })
    }
  }

  const removeFromCart = (productId) => {
    state.items = state.items.filter(i => i.id !== productId)
  }

  const updateQuantity = (productId, delta) => {
    const item = state.items.find(i => i.id === productId)
    if (item) {
      item.quantity += delta
      if (item.quantity <= 0) {
        removeFromCart(productId)
      }
    }
  }

  const totalItems = computed(() => state.items.reduce((sum, item) => sum + item.quantity, 0))

  const total = computed(() => {
    const allStickers = []
    state.items.forEach(item => {
      for (let i = 0; i < item.quantity; i++) {
        allStickers.push(item.price)
      }
    })

    const totalQty = allStickers.length
    const numBundles = Math.floor(totalQty / 10)
    const remainingCount = totalQty % 10

    // Sort prices so that we can apply the discount to a specific set
    // It's simplest to just sum the remaining ones and add the bundle price
    // To be fair and consistent, let's sort ascending and take the last 'remainingCount' items
    allStickers.sort((a, b) => a - b)
    
    let sum = numBundles * 25.00
    // The 'remaining' ones are the ones at the end of the sorted array
    const remainingStickers = allStickers.slice(allStickers.length - remainingCount)
    sum += remainingStickers.reduce((s, p) => s + p, 0)
    
    return sum
  })

  const generateWhatsappLink = () => {
    const phoneNumber = '5511999999999' // placeholder
    let text = 'Olá! Gostaria de encomendar os seguintes stickers da Calco.Mania:%0A%0A'
    state.items.forEach(i => {
      text += `- ${i.quantity}x ${i.name} (R$ ${i.price.toFixed(2)} cada)%0A`
    })
    
    const qty = totalItems.value
    if (qty >= 10) {
        text += `%0A*Promoção Aplicada:* ${Math.floor(qty/10)} conjunto(s) de 10 por R$ 25,00 cada!%0A`
    }
    
    text += `%0ATotal: R$ ${total.value.toFixed(2)}`
    return `https://wa.me/${phoneNumber}?text=${text}`
  }

  return {
    state,
    addToCart,
    removeFromCart,
    updateQuantity,
    totalItems,
    total,
    generateWhatsappLink
  }
}
