document.addEventListener('DOMContentLoaded', function () {
    const topbar = document.querySelector('.topbar');
    // sự kiện cuộn trang
    window.addEventListener('scroll', function () {
        const topbarRect = topbar.getBoundingClientRect();
        if (topbarRect.top < -10) {
            topbar.classList.add('topbar-animation');
        } else {
            topbar.classList.remove('topbar-animation');
        }
    });


    // gán sự kiện search:
    document.querySelector('.search-btn').addEventListener('click', search);
    document
        .querySelectorAll(".search-condition input[type='radio'], .search-condition input[type='checkbox']")
        .forEach((input) => input.addEventListener("change", search));

});

function search() {
    console.log("search");
}

function toggleList(element) {
    const parent = element.parentElement;
    parent.classList.toggle("open");
}