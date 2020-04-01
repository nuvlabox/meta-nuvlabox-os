SUMMARY = "Check if Docker Swarm mode is enabled"
DESCRIPTION = "Check if Docker Swarm mode is enabled on every boot.\
If Docker is not running in Swarm mode then this script will attempt \
to enable it. \
If Swarm mode is already enabled, then we leave it be."
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit systemd

SRC_URI += "\
	file://check-swarm-mode \
	file://check-swarm-mode-cron \
"

RDEPENDS_${PN} += " cronie"

do_install_append() {

		# Install the executable
		install -d ${D}${sbindir}
		install -m 0755 ${WORKDIR}/check-swarm-mode ${D}${sbindir}/

		# Install the cronjob
		install -d ${D}${sysconfdir}/cron.d
		install -m 0644 ${WORKDIR}/check-swarm-mode-cron ${D}${sysconfdir}/cron.d/
		sed -i -e 's,@SBINDIR@,${sbindir},g' \
                    ${D}${sysconfdir}/cron.d/check-swarm-mode-cron

}

FILES_${PN} += " \
	${sbindir}/check-swarm-mode \
	${sysconfdir}/cron.d/check-swarm-mode-cron \
"
