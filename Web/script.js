// 내비게이션 바와 메뉴 버튼 요소 선택
const navbar = document.querySelector('.navbar');
const menuToggle = document.querySelector('.menu-toggle');
const navMenu = document.querySelector('.nav-menu');

// 메뉴 항목들 선택
const navItems = document.querySelectorAll('.nav-menu li');

// 애니메이션 클래스 추가 및 제거 함수
function addAnimationClass() {
    navMenu.classList.add('active');
    navItems.forEach((item, index) => {
        item.style.animation = `slideIn 0.3s ease-out forwards ${0.2 * index}s`;
    });
}

function removeAnimationClass() {
    navItems.forEach((item, index) => {
        item.style.animation = `slideOut 0.3s ease-in forwards ${0.2 * index}s`;
    });
    setTimeout(() => {
        navMenu.classList.remove('active');
    }, 300 + 0.2 * (navItems.length - 1) * 1000);
}

// 햄버거 버튼 클릭 시 메뉴 토글
menuToggle.addEventListener('click', () => {
    if (navMenu.classList.contains('active')) {
        removeAnimationClass();
    } else {
        addAnimationClass();
    }
});

// 화면 크기 변경 감지
function handleResize() {
    if (window.innerWidth > 700) {
        // 화면 크기가 700px 이상일 때
        navMenu.classList.remove('active');
        navItems.forEach(item => {
            item.style.animation = 'none'; // 애니메이션 초기화
        });
    } else if (navMenu.classList.contains('active')) {
        // 화면 크기가 700px 이하일 때 메뉴가 활성화된 상태
        addAnimationClass();
    }
}

// 초기화 및 화면 크기 변경 시 처리
window.addEventListener('resize', handleResize);
window.addEventListener('load', handleResize); // 페이지 로드 시에도 실행


document.addEventListener('DOMContentLoaded', () => {
    const sections = document.querySelectorAll('main section');
    const navLinks = document.querySelectorAll('.nav-menu a');
    const logoLink = document.querySelector('.logo-link');

    // 모든 섹션을 숨기고 선택된 섹션만 표시하는 함수
    function showSection(sectionId) {
        sections.forEach((section) => {
            if (section.id === sectionId) {
                section.classList.remove('hidden');
                section.classList.add('visible');
            } else {
                section.classList.add('hidden');
                section.classList.remove('visible');
            }
        });
    }

    // 페이지 로드 시 Intro 섹션만 보이도록 초기화
    showSection('home');

    // 내비게이션 메뉴 항목에 클릭 이벤트 추가
    navLinks.forEach((link) => {
        link.addEventListener('click', function (e) {
            e.preventDefault(); // 기본 앵커 동작 방지
            const target = this.getAttribute('href').substring(1); // 섹션 ID 가져오기

            showSection(target); // 해당 섹션 표시
        });
    });

    // 로고 클릭 시 Intro로 돌아가기
    logoLink.addEventListener('click', (e) => {
        e.preventDefault(); // 기본 앵커 동작 방지
        showSection('home');
    });
});
