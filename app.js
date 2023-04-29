const copyBtns = document.querySelectorAll(".copy-btn");

copyBtns.forEach(btn => {
  const codeContainer = btn.previousElementSibling;
  const code = codeContainer.querySelector("code").innerText;

  btn.addEventListener("click", () => {
    navigator.clipboard.writeText(code).then(() => {
      btn.innerText = "Copié !";
      setTimeout(() => {
        btn.innerText = "Copier";
      }, 1000);
    });
  });
});