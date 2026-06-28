/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        navy: {
          900: '#070B19',
          800: '#0F1832',
          700: '#1E293B',
        },
        cyber: {
          blue: '#1E90FF',
          teal: '#00FFFF',
          magenta: '#FF00FF',
          green: '#39FF14',
          yellow: '#FFD700',
        }
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
        mono: ['JetBrains Mono', 'monospace'],
      },
      boxShadow: {
        'glow-blue': '0 0 15px rgba(30, 144, 255, 0.5)',
        'glow-teal': '0 0 15px rgba(0, 255, 255, 0.5)',
        'glow-magenta': '0 0 15px rgba(255, 0, 255, 0.5)',
      }
    },
  },
  plugins: [],
}
