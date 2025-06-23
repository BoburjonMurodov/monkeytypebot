# 🐵 Monkeytype OCR Bot

A desktop app that cheats at [Monkeytype](https://monkeytype.com) so you don’t have to prove you're human — let the machine type *for* you.

Built with:

- 🧠 Tesseract OCR — because recognizing words by eye is too mainstream
- 🧰 Java Robot — your loyal virtual typist
- 🖼️ Swing — because writing your own UI is better than Electron
- ⚡ Kotlin — because verbosity is a disease

---

## 🎯 What does it do?

1. You drag and select the region of Monkeytype where the words appear.
2. It captures that screen area and OCRs the heck out of it.
3. Then it goes *brrrrr* and types everything as fast as your CPU allows.

Monkeytype? More like *Botkeytype*. 😎

---

## 🖼️ Features

- 🖱️ Click and drag to pick the text area
- 🧙 Magic OCR with Tesseract
- ⌨️ Types automatically using `Robot` (acts like a real keyboard)
- 🔘 Simple 2-button UI: one for capturing, one for chaos

---

## ⚙️ Setup

### 🧟 Dependencies

- Java 17+ (don't ask why, just do it)
- Kotlin (if you're building from source)
- Tesseract OCR — this one’s a pain in the ass, yeah...

### 🛠️ How to Install Tesseract

#### 🧑‍💻 macOS:

```bash
brew install tesseract
